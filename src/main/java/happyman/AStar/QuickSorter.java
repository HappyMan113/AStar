package happyman.AStar;

public class QuickSorter
{
    static<T extends Comparable<? super T>> void quickSort(final T[] A)
    {
        quickSort(A, 0, A.length - 1);
    }

    private static<T extends Comparable<? super T>> int partition(T[] A, int l, int r, T pivot)
    {
        --l; //have to do this this way because if you take the value then in/decrement you'll swap the wrong values
        ++r; //ditto ^
        do
        {
            //find 2 values that are both on wrong sides and swap them
            while (A[++l].compareTo(pivot) < 0); //no need to check for l == length - 1 because the last value is the pivot and it's compare will be 0 if l reaches it (which only happens when pivot is biggest or tied for biggest, making the whole partition a left partition)
            while (r != 0 && A[--r].compareTo(pivot) > 0); //need to check r != 0 because the pivot might be the smallest value in the partition (making the whole partition a right partition)
            swap(A, l, r);
        } while (l < r);
        swap(A, l, r); //because that last swap had l >= r
        return l; //the first index in the right partition because ?
    }

    private static<T extends Comparable<? super T>> void quickSort(T[] A, int i, int j)
    {
        if (i < j)
        {
            int pivot = (i + j) / 2; //findPivot(A, i, j);
            swap(A, pivot, j);
            pivot = partition(A, i, j - 1, A[j]);
            swap(A, pivot, j);
            quickSort(A, i, pivot - 1);
            quickSort(A, pivot + 1, j);
        }
    }

    static<T> void swap(T[] A, int i1, int i2)
    {
        T temp = A[i1];
        A[i1] = A[i2];
        A[i2] = temp;
    }
}
