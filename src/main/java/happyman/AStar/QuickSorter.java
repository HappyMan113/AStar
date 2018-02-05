package happyman.AStar;

public class QuickSorter
{
    public static<T extends Comparable<? super T>> void quickSort(final T[] array)
    {
        if (array.length > 1)
        {
            quickSort(array, 0, array.length - 1);
        }
    }

    private static<T extends Comparable<? super T>> int partition(T[] array, int left, int right, T pivot)
    {
        --left; //have to do this this way because if you take the value then in/decrement you'll swap the wrong values
        ++right; //ditto ^
        do
        {
            //find 2 values that are both on wrong sides and swap them
            while (left < right && array[++left].compareTo(pivot) < 0); //need to check for l == length - 1 because the last value is the pivot and it's compare might not be 0 even if l reaches it (which only happens when pivot is biggest or tied for biggest, making the whole partition a left partition)
            while (right > left && array[--right].compareTo(pivot) > 0); //need to check r != 0 because the pivot might be the smallest value in the partition (making the whole partition a right partition)
            swap(array, left, right);
        } while (left < right);
        swap(array, left, right); //because that last swap had l >= r
        return left; //the first index in the right partition because ?
    }

    private static<T extends Comparable<? super T>> void quickSort(T[] array, int left, int right)
    {
        if (left < right)
        {
            if (right - left < 10)
            {
                insertionSort(array, left, right);
            }
            else
            {
                int pivot = (left + right) / 2;
                swap(array, pivot, right);
                pivot = partition(array, left, right - 1, array[right]);
                swap(array, pivot, right);
                quickSort(array, left, pivot - 1);
                quickSort(array, pivot + 1, right);
            }
        }
    }

    private static<T extends Comparable<? super T>> void insertionSort(T[] array, int left, int right)
    {
        for (int i = left + 1; i <= right; i++)
        {
            final T key = array[i];
            int j = i - 1;
            while (j >= left && array[j].compareTo(key) > 0)
            {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private static<T> void swap(T[] array, int index1, int index2)
    {
        T temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}
