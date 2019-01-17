package manuel.fun.arma3.model;

import org.springframework.data.repository.CrudRepository;

public interface SourceRepo extends CrudRepository<SourceEntry,Long>{
	SourceEntry findBymodname(String name);

}
