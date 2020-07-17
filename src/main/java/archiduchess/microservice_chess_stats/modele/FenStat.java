package archiduchess.microservice_chess_stats.modele;

public class FenStat {

	String user;
	String fen;
	int playedGames;
	double efficiency;
	
	public FenStat() {
		// TODO Auto-generated constructor stub
	}
	
	
		public FenStat(String user, String fen, int playedGames, double efficiency) {
		super();
		this.user = user;
		this.fen = fen;
		this.playedGames = playedGames;
		this.efficiency = efficiency;
	}




	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getFen() {
		return fen;
	}
	public void setFen(String fen) {
		this.fen = fen;
	}
	public int getPlayedGames() {
		return playedGames;
	}
	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}
	public double getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
	
	
}
