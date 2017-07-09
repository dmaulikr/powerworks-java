package powerworks.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import powerworks.collidable.Collidable;

public class SpatialOrganizer<T extends Collidable> implements Iterable<T> {

    List<T> objects = new ArrayList<T>();

    public List<T> getIntersecting(int x, int y, int width, int height) {
	List<T> returnObj = new ArrayList<T>();
	objects.stream().filter((T t) -> {
	    return GeometryHelper.intersects(x, y, width, height, t.getXPixel() + t.getHitbox().getXStart(), t.getYPixel() + t.getHitbox().getYStart(), t.getHitbox().getWidthPixels(),
		    t.getHitbox().getHeightPixels());
	}).forEach(returnObj::add);
	return returnObj;
    }

    public List<T> getIntersecting(int x, int y, int width, int height, Predicate<T> condition) {
	List<T> returnObj = new ArrayList<T>();
	objects.stream().filter((T t) -> {
	    return condition.test(t)
		    && GeometryHelper.intersects(x, y, width, height, t.getXPixel() + t.getHitbox().getXStart(), t.getYPixel() + t.getHitbox().getYStart(), t.getHitbox().getWidthPixels(),
			    t.getHitbox().getHeightPixels());
	}).forEach(returnObj::add);
	return returnObj;
    }

    public boolean anyIntersecting(int x, int y, int width, int height, Predicate<T> condition) {
	return objects.stream().filter(condition).anyMatch((T t) -> GeometryHelper.intersects(x, y, width, height, t.getXPixel() + t.getHitbox().getXStart(), t.getYPixel() + t.getHitbox().getYStart(), t.getHitbox().getWidthPixels(),
		    t.getHitbox().getHeightPixels()));
    }

    public boolean anyIntersecting(int x, int y, int width, int height) {
	return objects.stream().anyMatch((T t) -> {
	    return GeometryHelper.intersects(x, y, width, height, t.getXPixel() + t.getHitbox().getXStart(), t.getYPixel() + t.getHitbox().getYStart(), t.getHitbox().getWidthPixels(),
		    t.getHitbox().getHeightPixels());
	});
    }

    public List<T> query(Predicate<? super T> p) {
	List<T> returnObj = new ArrayList<T>();
	objects.stream().filter(p).forEach(returnObj::add);
	return returnObj;
    }

    public List<T> getContainedBy(int x, int y, int width, int height) {
	List<T> returnObj = new ArrayList<T>();
	objects.stream().filter((T t) -> {
	    return GeometryHelper.contains(x, y, width, height, t.getXPixel() + t.getHitbox().getXStart(), t.getYPixel() + t.getHitbox().getYStart(), t.getHitbox().getWidthPixels(),
		    t.getHitbox().getHeightPixels());
	}).forEach(returnObj::add);
	return returnObj;
    }

    public void add(T t) {
	objects.add(t);
    }

    public void remove(T t) {
	Iterator<T> i = objects.iterator();
	while (i.hasNext()) {
	    T t1 = i.next();
	    if (t1 == t)
		i.remove();
	}
    }
    
    public int size() {
	return objects.size();
    }

    @Override
    public Iterator<T> iterator() {
	Iterator<T> i = new Iterator<T>() {

	    Iterator<T> i2 = objects.iterator();

	    @Override
	    public boolean hasNext() {
		return i2.hasNext();
	    }

	    @Override
	    public T next() {
		return i2.next();
	    }
	};
	return i;
    }
}
