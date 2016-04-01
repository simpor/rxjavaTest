package rxcircle;

import org.junit.Test;
import rx.Observable;
import rx.observables.ConnectableObservable;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HelloWorldTest {

    @Test
    public void testGet() throws Exception {
        assertEquals("World", HelloWorld.get());
    }

    @Test
    public void testFlatMap() {
        ConnectableObservable<String> obs = Observable.from(Arrays.asList("One", "Two", "Three", "Four")).publish();
        obs.subscribe(s -> System.out.println("1: " + s));
        obs.subscribe(s -> System.out.println("2: " + s));

        Observable<String> obs2 = obs.flatMap(this::createFourStrings);
        obs2.subscribe(s -> System.out.println("3: " + s));
        obs2.subscribe(s -> System.out.println("4: " + s));

        obs.connect();
    }

    private Observable<String> createFourStrings(String s) {
        return Observable.create(subscriber -> {
            for (int i = 0; i < 4; i++) {
                subscriber.onNext(s + i);
            }
        });
    }

}