import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume == null) return null;
            else if (resume.uuid.equals(uuid)) return resume;
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) break;
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                break;
            }
        }

        Arrays.sort(storage, new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                if ((o1 == null && o2 == null) || (o1 != null && o2 != null)) return 0;
                else if (o1 == null) return 1;
                else if (o2 == null) return -1;
                return 0;
            }
        });
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                return i;
            }
        }
        return storage.length;
    }
}
