package Project101.FileSystem;

import lombok.Getter;

@Getter
public class ListOptions {
    private boolean showHidden;      // -a
    private boolean showDetails;     // -l
    private boolean sortBySize;      // -S
    private boolean sortDescending;  // -r
    ListOptions(boolean showHidden,boolean showDetails,boolean sortBySize,boolean sortDescending){
        this.showHidden = showHidden;
        this.showDetails = showDetails;
        this.sortBySize = sortBySize;
        this.sortDescending = sortDescending;
    }
}
