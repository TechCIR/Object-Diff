package techcr.utils.object.diff.type.config;


public class JobValueOutputHandler implements ValueOutputHandler<String> {
    @Override
    public String print(String s) {
        return "Job : " + s;
    }
}
