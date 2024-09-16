package projects.liga.config.params_handling;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParamsHandler {

    List<Optional<Runnable>> getRunnableTasksFromParamsMap(Map<String, String> paramsMap);

}
