public class Bus {
    private int id;
    private String name;
    private int capacity;

    public Bus(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @Override
    public String toString() {
        return "Bus{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", capacity=" + capacity +
               '}';
    }
}
