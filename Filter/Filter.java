package Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Filter<T> {
    public interface FilterParam<T> {
        public boolean isGood(final T item);
    }

    public Filter() { }
    
    public Collection<T> filter(Collection<T> items, Collection<FilterParam<T>> filters) {
        List<T> ans = new ArrayList<>(items.size());
        for (T item : items) {
            boolean good = true;
            for (FilterParam<T> filter : filters) {
                good &= filter.isGood(item);
            }
            if(good) {
                ans.add(item);
            }
        }
        return ans;
    }
}

