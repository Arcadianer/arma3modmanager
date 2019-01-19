
package manuel.fun.arma3.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTask {
	public UpdateTask(SourceEntry source) {
		this.source = source;
		status=TaskStatus.waiting;
		exceptions=new ArrayList<>();
	}
	SourceEntry source;
	TaskStatus status;
	ArrayList<Exception> exceptions;
	

}
