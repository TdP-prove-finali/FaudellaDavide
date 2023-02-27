package it.polito.tdp.FaudellaDavide.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.FaudellaDavide.model.Libro;

public class LibriDAO {

	public List<Libro> getAllBooks() {
		final String sql = "SELECT * FROM Libro";
		List<Libro> result = new LinkedList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Libro(res.getInt("NumID"), res.getFloat("Prezzo"), res.getInt("Rank"),
						res.getString("Titolo"), res.getInt("Numero_recensioni"), res.getFloat("Rating"),
						res.getString("Autore"), res.getString("Tipo_copertina"), res.getInt("Anno"),
						res.getString("Genere")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}

	public List<Libro> getBooksParametri(float rating, int numero, int annoI, int annoF, String copertina,
			String genere) {
		final String sql = "SELECT * " + "FROM libro "
				+ "WHERE Rating > ? AND Numero_recensioni > ? AND Anno >= ? AND anno <= ? AND Tipo_copertina = ? AND Genere = ? ";
		List<Libro> result = new LinkedList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setFloat(1, rating);
			st.setInt(2, numero);
			st.setInt(3, annoI);
			st.setInt(4, annoF);
			st.setString(5, copertina);
			st.setString(6, genere);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Libro(res.getInt("NumID"), res.getFloat("Prezzo"), res.getInt("Rank"),
						res.getString("Titolo"), res.getInt("Numero_recensioni"), res.getFloat("Rating"),
						res.getString("Autore"), res.getString("Tipo_copertina"), res.getInt("Anno"),
						res.getString("Genere")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}

	public List<String> getAllAuthors() {
		final String sql = "SELECT distinct Autore " + "FROM libro " + "ORDER BY autore";
		List<String> result = new LinkedList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Autore"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}

	public List<Integer> getAllYears() {
		final String sql = "SELECT distinct anno " + "FROM libro " + "ORDER BY anno";
		List<Integer> result = new LinkedList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getInt("Anno"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}

	public List<String> getAllGenres() {
		final String sql = "SELECT DISTINCT Genere " + "FROM libro " + "ORDER BY Genere";
		List<String> result = new LinkedList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Genere"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}

	public List<String> getAllCoverTypes() {
		final String sql = "SELECT DISTINCT Tipo_copertina " + "FROM libro " + "ORDER BY Tipo_copertina";
		List<String> result = new LinkedList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Tipo_copertina"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}

	public List<Libro> getLibriPerAutore(String autore) {
		final String sql = "SELECT * " + "FROM libro " + "WHERE Autore = ? ";
		List<Libro> result = new LinkedList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, autore);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Libro(res.getInt("NumID"), res.getFloat("Prezzo"), res.getInt("Rank"),
						res.getString("Titolo"), res.getInt("Numero_recensioni"), res.getFloat("Rating"),
						res.getString("Autore"), res.getString("Tipo_copertina"), res.getInt("Anno"),
						res.getString("Genere")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
}
