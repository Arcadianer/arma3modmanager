
package manuel.fun.arma3.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SourceEntry {
	@Id
	private Long workshopid;
	private Long updatetime;
	private String modname;
	private String keyfilename;
	

}
