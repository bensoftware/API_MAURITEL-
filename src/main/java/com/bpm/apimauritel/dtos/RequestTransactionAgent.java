package mr.bpm.bankily.dot;

import java.io.Serializable;
import java.util.Date;

public class RequestTransactionAgent implements Serializable {

	private static final long serialVersionUID = 1L;
	String codeAgent;
	Date dateDebut;
	Date dateFin;
	
	public String getCodeAgent() {
		return codeAgent;
	}
	public void setCodeAgent(String codeAgent) {
		this.codeAgent = codeAgent;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	
	

	
}
