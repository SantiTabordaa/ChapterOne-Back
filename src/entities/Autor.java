package entities;

public class Autor {

	private int idAutor;
	private String nombre;
	private String apellido;
	private String Pseudonimo;
	private String nacionalidad;
	private String urlFoto;
	public int getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getPseudonimo() {
		return Pseudonimo;
	}
	public void setPseudonimo(String pseudonimo) {
		Pseudonimo = pseudonimo;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	public Autor() {
		
	}
}
