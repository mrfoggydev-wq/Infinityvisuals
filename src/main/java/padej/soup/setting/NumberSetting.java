package padej.soup.setting;
import lombok.Getter;
@Getter public final class NumberSetting { private final String name; private final double min,max; private double value; public NumberSetting(String name,double min,double max,double value){this.name=name;this.min=min;this.max=max;set(value);} public void set(double value){this.value=Math.max(min,Math.min(max,value));} }
