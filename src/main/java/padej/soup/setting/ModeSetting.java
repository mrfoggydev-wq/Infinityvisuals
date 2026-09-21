package padej.soup.setting;
import lombok.Getter;
@Getter public final class ModeSetting { private final String name; private final String[] modes; private int index; public ModeSetting(String name,String... modes){this.name=name;this.modes=modes;} public String getValue(){return modes.length==0?"":modes[index];} public void next(){if(modes.length>0)index=(index+1)%modes.length;} }
