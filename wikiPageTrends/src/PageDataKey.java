import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

/**
 * @author ashish
 *
 */
class PageDataKey implements WritableComparable<PageDataKey> {

	public String pageTitle;
	public String date;

	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, pageTitle);
		WritableUtils.writeString(out, date);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.pageTitle = WritableUtils.readString(in);
		this.date = WritableUtils.readString(in);
	}

	@Override
	public int compareTo(PageDataKey o) {
		if (o == null) {
			return 0;
		} else if (pageTitle.compareTo(o.pageTitle) != 0) {
			return pageTitle.compareTo(o.pageTitle);
		} else if (date.compareTo(o.date) != 0) {
			return date.compareTo(o.date);
		}

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return pageTitle + "-" + date;
	}
}
