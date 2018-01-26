package happyman.AStar;

import java.util.ArrayList;

public class PriorityQueue<T extends Comparable<? super T>>
{
    private final ArrayList<T> data;

    PriorityQueue()
    {
        this.data = new ArrayList<>();
    }

    final void add(T newElement)
    {
        if (newElement != null)
        {
            Node element = new Node(size());
            element.setData(newElement);

            while (!element.isRoot())
            {
                Node parent = element.getParent();
                if (compare(element.getData(), parent.getData()) <= 0)
                {
                    break;
                }
                element.swapData(parent);
                element = parent;
            }
        }
    }

    final T remove()
    {
        final T result;
        Node root = new Node(0);
        result = root.getData();
        if (result != null)
        {
            Node elementToDelete = new Node(size() - 1);
            root.swapData(elementToDelete);
            elementToDelete.removeFromHeap();
            while (!root.isLeaf())
            {
                Node leftChild = root.getLeftChild();
                Node rightChild = root.getRightChild();

                Node biggerChild = compare(leftChild.getData(),
                        rightChild.getData()) >= 0 ? leftChild : rightChild;

                if (compare(root.getData(), biggerChild.getData()) >= 0)
                {
                    break;
                }
                root.swapData(biggerChild);
                root = biggerChild;
            }
        }
        return result;
    }

    private int compare(T element1, T element2)
    {
        return element1 == null || element2 == null ? 0 :
                element2.compareTo(element1);
    }

    private int size()
    {
        return data.size();
    }

    final boolean isEmpty()
    {
        return data.isEmpty();
    }

    private class Node
    {
        private final int index;

        private Node(int index)
        {
            this.index = index;
        }

        private T getData()
        {
            return index >= size() ? null : data.get(index);
        }

        private void setData(T newData)
        {
            if (newData == null)
            {
                data.remove(index);
            }
            else if (index == size())
            {
                data.add(newData);
            }
            else
            {
                data.set(index, newData);
            }
        }

        private void swapData(Node other)
        {
            T temp = getData();
            setData(other.getData());
            other.setData(temp);
        }

        private Node getLeftChild()
        {
            return new Node(2 * index + 1);
        }

        private Node getRightChild()
        {
            return new Node(2 * index + 2);
        }

        private Node getParent()
        {
            return new Node(isLeftChild() ? (index - 1) / 2 :
                    (index - 2) / 2);
        }

        private boolean isLeaf()
        {
            return index >= size() / 2;
        }

        private boolean isLeftChild()
        {
            return index % 2 == 1;
        }

        private boolean isRoot()
        {
            return index == 0;
        }

        private void removeFromHeap()
        {
            data.remove(index);
        }
    }
}
