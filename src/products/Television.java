package products;

public class Television extends Product {

	private int ScreenSize;
	private String resolution;
	public Television(int pid, String brand, String model, int ScreenSize, String resolution) {
		super(pid, brand, model);
		this.ScreenSize = ScreenSize;
		this.resolution = resolution;
	}
	public int getScreenSize() {
		return ScreenSize;
	}
	public void setScreenSize(int screenSize) {
		ScreenSize = screenSize;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	@Override
	public String toString() {
		return super.toString() + " --Television [ScreenSize=" + ScreenSize + ", Resoluion=" + resolution + "]";
	}

}
