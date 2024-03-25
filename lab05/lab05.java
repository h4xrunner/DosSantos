/*
* @author ibrahim Gül
* @since 21.03.2024
*/
import java.util.ArrayList;
import java.util.Arrays;


class Computer {
    private CPU cpu;
    private RAM ram;
    public CPU getCpu() {
        return cpu;
    }
    public RAM getRam() {
        return ram;
    }
        //hocam akşama kadar uğraştım hocam derste size yetişemedim şu an programming labdayım bilgisayarım çalındı.
    Computer(CPU cpu, RAM ram) {
        this.cpu = cpu;
        this.ram = ram;
    }

    public void run() {
        int sum = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            sum += ram.getValue(i, i);
        }
        cpu.compute(sum, 0);
    }

    public String toString() {
        return "Computer: " + cpu + " " + ram;
    }
}

class Laptop extends Computer {
    private int milliAmp;
    private int battery;

    Laptop(CPU cpu, RAM ram, int milliAmp) {
        super(cpu, ram);
        this.milliAmp = milliAmp;
        this.battery = milliAmp * 3 / 10;
    }

    public int batteryPercentage() {
        return (battery * 100) / milliAmp;
    }

    public void charge() {
        while (batteryPercentage() < 90) {
            battery += milliAmp * 2 / 100;
        }
        if (battery > milliAmp) {
            battery = milliAmp;
        }
    }

    @Override
    public void run() {
        if (batteryPercentage() > 5) {
            super.run();
            battery -= milliAmp * 3 / 100;
        } else {
            charge();
        }
    }

    public String toString() {
        return super.toString() + " " + battery;
    }
}

class Desktop extends Computer {
    private ArrayList<String> peripherals;

    Desktop(CPU cpu, RAM ram, String... peripherals) {
        super(cpu, ram);
        this.peripherals = new ArrayList<>(Arrays.asList(peripherals));
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < getRam().getCapacity(); i++) {
            for (int j = 0; j < getRam().getCapacity(); j++) {
                sum = getCpu().compute(sum, getRam().getValue(i, j));
            }
        }
        getRam().setValue(0, 0, sum);
    }

    public void plugIn(String peripheral) {
        peripherals.add(peripheral);
    }

    public String plugOut() {
        if (!peripherals.isEmpty()) {
            return peripherals.remove(peripherals.size() - 1);
        } else {
            return "No peripheral";
        }
    }

    public String plugOut(int index) {
        if (index >= 0 && index < peripherals.size()) {
            return peripherals.remove(index);
        } else {
            return "invalid index";
        }
    }

    public String toString() {
        return super.toString() + " " + String.join(" ", peripherals);
    }

    // Yeni eklenen metotlar
    public ArrayList<String> getPeripherals() {
        return peripherals;
    }

    public void setPeripherals(ArrayList<String> peripherals) {
        this.peripherals = peripherals;
    }
}


class CPU {
    private String name;
    private double clock;

    CPU(String name, double clock) {
        this.name = name;
        this.clock = clock;
    }

    public String getName() {
        return name;
    }

    public double getClock() {
        return clock;
    }

    public int compute(int a, int b) {
        int sum = a + b;
        return sum;
    }

    public String toString() {
        return "CPU" + name + " " + clock + "Ghz";
    }
}

class RAM {
    private String type;
    private int capacity;
    private int[][] memory;

    RAM(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
        this.initMemory();
    }

    private void initMemory() {
        memory = new int[capacity][capacity];
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < capacity; j++) {
                memory[i][j] = generateRandomNumber(0, 10);
            }
        }
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getValue(int i, int j) {
        if (check(i, j)) {
            return -1;
        } else {
            return memory[i][j];
        }
    }

    public void setValue(int i, int j, int value) {
        if (check(i, j)) {
            memory[i][j] = value;
        } else {
            System.out.println("invalid value");
            throw new IllegalArgumentException();
        }
    }

    private boolean check(int i, int j) {
        if (i < 0 || i >= capacity) {
            return true;
        } else return j < 0 || j >= capacity;
    }

    private int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public String toString() {
        return "RAM:" + type + " " + capacity + "GB";
    }
}
