package padej.soup.setting;
import lombok.Getter;
@Getter public final class BooleanSetting { private final String name; private boolean value; public BooleanSetting(String name, boolean value){this.name=name;this.value=value;} public void toggle(){value=!value;} public void set(boolean value){this.value=value;} }
