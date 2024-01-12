package Filter;

public class FilterInstanceOf<T> implements Filter.FilterParam<T> {

    @Override
    public boolean isGood(final T person) {
        return person != null && person instanceof T;
    }
    
}
