package products;

public class CellPhone extends Product {
	private int storageSize;
	private String ramSize;

	public CellPhone(int pid, String brand, String model, int storageSize, String ramSize) {
		super(pid, brand, model);
		this.storageSize = storageSize;
		this.ramSize = ramSize;
	}

	public int getStorageSize() {
		return storageSize;
	}

	public void SetStorageSize(int storageSize) {
		this.storageSize = storageSize;
	}

	public String getRamSize() {
		return ramSize;
	}

	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
	}

	@Override
	public String toString() {
		return super.toString() + " --CellPhone [Storage Size=" + storageSize + ", ramSize=" + ramSize + "]";
	}

}
