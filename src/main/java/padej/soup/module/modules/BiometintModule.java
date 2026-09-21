package padej.soup.module.modules;
import lombok.Getter;
import padej.soup.module.Module;
import padej.soup.module.ModuleCategory;
import padej.soup.setting.BooleanSetting;
import padej.soup.setting.ModeSetting;
import padej.soup.setting.NumberSetting;
@Getter public final class BiometintModule extends Module {
 private final BooleanSetting smooth=new BooleanSetting("Smooth",true);
 private final BooleanSetting rainbow=new BooleanSetting("Rainbow",true);
 private final NumberSetting intensity=new NumberSetting("Intensity",0.0,1.0,0.75);
 private final ModeSetting mode=new ModeSetting("Mode","Clean","Pulse","Rainbow");
 public BiometintModule(){super("BiomeTint","Biome color accents",ModuleCategory.WORLD);}
}
