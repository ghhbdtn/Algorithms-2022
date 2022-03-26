package lesson4;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Map.Entry;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        SortedMap<Character, Node> children = new TreeMap<>();
    }

    private final Node root = new Node();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove (Object o){
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     *
     * Сложная
     */

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

    public class TrieIterator implements Iterator<String> {

        private String next;
        private final Deque<Iterator<Entry<Character, Node>>> stack = new ArrayDeque<>();
        private final StringBuilder sb = new StringBuilder();
        int count = 0;

        private TrieIterator() {
           stack.push(root.children.entrySet().iterator());
        }

        public void findNext() {
            // Трудоёмксость: O(N - суммарная длина всех слов в худшем случае)
            // Ресурсоёмкость: O(h - длина самого длинного слова)
            Iterator<Entry<Character, Node>> iterator = stack.peek();
            while (iterator != null){
                while (iterator.hasNext()) {
                    Entry<Character, Node> entry = iterator.next();
                    Node value = entry.getValue();
                    char key = entry.getKey();
                    if (key == (char) 0) {
                        count++;
                        next = sb.toString();
                        return;
                    }
                    sb.append(key);
                    iterator = value.children.entrySet().iterator();
                    stack.push(iterator);
                }
                stack.pop();
                if (!sb.isEmpty()) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                iterator = stack.peek();
            }
        }

        @Override
        public boolean hasNext() {
            // Трудоёмксость: O(1)
            // Ресурсоёмкость: O(1)
            return count < size;
        }

        @Override
        public String next() {
            // Трудоёмксость: O(N - суммарная длина всех слов в худшем случае)
            // Ресурсоёмкость: O(h - длина самого длинного слова)
            if (!hasNext()) throw new NoSuchElementException();
            findNext();
            return next;
        }

        @Override
        public void remove() {
            // Трудоёмксость: O(1)
            // Ресурсоёмкость: O(1)
            if (next == null) throw new IllegalStateException();
            if (stack.peek() != null) {
                stack.peek().remove();
                next = null;
                size--;
                count--;
            }
        }
    }
}