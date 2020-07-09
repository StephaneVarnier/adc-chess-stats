package archiduchess.microservice_chess_stats.beans;

public class OnlineGameBean {

private String id; 
	
	private String playerWhite;
	private String playerBlack;
	
	private int eloWhite;
	private int eloBlack;
	
	private String date;

	private String timeControl;
	
	private String resultat;
	private String opening;
	
	private String pgn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayerWhite() {
		return playerWhite;
	}

	public void setPlayerWhite(String playerWhite) {
		this.playerWhite = playerWhite;
	}

	public String getPlayerBlack() {
		return playerBlack;
	}

	public void setPlayerBlack(String playerBlack) {
		this.playerBlack = playerBlack;
	}

	public int getEloWhite() {
		return eloWhite;
	}

	public void setEloWhite(int eloWhite) {
		this.eloWhite = eloWhite;
	}

	public int getEloBlack() {
		return eloBlack;
	}

	public void setEloBlack(int eloBlack) {
		this.eloBlack = eloBlack;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTimeControl() {
		return timeControl;
	}

	public void setTimeControl(String timeControl) {
		this.timeControl = timeControl;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public String getOpening() {
		return opening;
	}

	public void setOpening(String opening) {
		this.opening = opening;
	}

	public String getPgn() {
		return pgn;
	}

	public void setPgn(String pgn) {
		this.pgn = pgn;
	}
	
	
}
