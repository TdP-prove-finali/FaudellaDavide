package it.polito.tdp.FaudellaDavide.db;

public class TestDB {

	public static void main(String[] args) {
		LibriDAO dao = new LibriDAO();
		
		System.out.println(dao.getBooksParametri(4, 100, 2009, 2020, "Paperback", "Fiction").size());

	}

}
