package pt.c02classes.s01knowledge.s02app.actors;

public class Position {
	private int x;
	private int y;
	
	public Position( int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public boolean igual(Position p){
		return ( x == p.getX() && y == p.getY() );
		
	}
	

}
