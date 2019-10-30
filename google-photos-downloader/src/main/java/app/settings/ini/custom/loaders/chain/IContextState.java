package app.settings.ini.custom.loaders.chain;

public interface IContextState {

    public void addCommentLine(String line);

    public void setHeaderComment();

    public void setActualSection(String sectionName);

    public void setItem(String key, String value);

    public boolean isError();

    public void setErrorMessage(String message);

    public String getErrorMessage();

}
