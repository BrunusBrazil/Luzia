package com.fatec.itu.santa_luzia01;

public class Localidade {

	private String rua = "";
	private String bairro = "Calculando";
	private String Cidade = "Calculando";

	
	
	public Localidade(){
		this.rua = "Calculando";
		this.Cidade = "Calculando";
		this.bairro = "Calculando";
	}
	
	
	public Localidade(String address){
		setBairro(address);
		setCidade(address);
		setRua(address);
	}
	
	/**
	 * @return the rua
	 */
	public String getRua() {
		return rua;
	}
	/**
	 * @param rua the rua to set
	 */
	public void setRua(String rua) {
		
		this.rua = rua.substring(0,14);
	}
	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}
	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro.substring(15,25);
	}
	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return Cidade;
	}
	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		Cidade = cidade.substring(26,30);
	}
	
	
}
