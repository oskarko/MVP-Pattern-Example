package es.org.mvpexample.models;

import java.util.Arrays;
import java.util.List;

/**
 * Created by oskarko on 4/11/16.
 */

public class FindItemsInteractorImpl implements FindItemsInteractor {

    @Override
    public void findItems(OnFinishedListener listener) {
        listener.onFinished(createArrayList());
    }

    private List<String> createArrayList() {
        return Arrays.asList(
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Item 5",
                "Item 6",
                "Item 7",
                "Item 8",
                "Item 9",
                "Item 10"
        );
    }
}
