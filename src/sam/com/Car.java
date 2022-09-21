package sam.com;

public class Car {
	//properties or state
	private String doors;
	private String engine;
 	private String driver;
 	private int speed;

 	//constructor
 	public Car() {
 		doors="opend";
 		engine="off";
 		driver="Absent";
 		speed =0;
 		
 	}
 	//overloading constructor here to initalized the custom properties
 	
 	public Car(String doors, String engine, String driver, int speed) {
		super();
		this.doors = doors;
		this.engine = engine;
		this.driver = driver;
		this.speed = speed;
	}

	//getter and setter
 	
 	
 		public int getSpeed() {
 		return speed;
 	}
	public String getDoors() {
		return doors;
	}
		public String getEngine() {
		return engine;
	}
	public String getDriver() {
		return driver;
	}
	public String run() {
		if(doors.equals("closed") && engine.equals("on") && driver.equals("seated") && speed>0 ) {
		return "running";
	}else {
		return "stop";
	}

}
}