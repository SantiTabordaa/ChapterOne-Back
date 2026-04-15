package entities;

public class Saga {
	
	private int idSaga;
	private String nombre;
	public int getIdSaga() {
		return idSaga;
	}
	public void setIdSaga(int idSaga) {
		this.idSaga = idSaga;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Saga(int idSaga, String nombre) {
		this.idSaga = idSaga;
		this.nombre = nombre;
	}
	public Saga() {
		
	}
	
	
	
}
