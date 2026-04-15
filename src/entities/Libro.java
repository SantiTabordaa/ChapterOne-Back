package entities;

public class Libro {

	private int idLibro;
	private String titulo;
	private String sinopsis;
	private Saga saga;
	private int cantPag;
	private double valoracion;
	
	
	
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	public Saga getSaga() {
		return saga;
	}
	public void setSaga(Saga saga) {
		this.saga = saga;
	}
	public int getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getCantPag() {
		return cantPag;
	}
	public void setCantPag(int cantPag) {
		this.cantPag = cantPag;
	}
	public double getValoracion() {
		//Se hace un promedio con las reseñas de el libro
		return valoracion;
	}
	public void setValoracion(double valoracion) {
		this.valoracion = valoracion;
	}
	
	public Libro() {
		
	}
	
}
