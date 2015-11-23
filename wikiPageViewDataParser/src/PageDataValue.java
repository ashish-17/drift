import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

/**
 * 
 */

/**
 * @author ashish
 *
 */
class PageDataValue implements WritableComparable<PageDataValue> {

	public int nthHour;
	public int countViews;

	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeVInt(out, nthHour);
		WritableUtils.writeVInt(out, countViews);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.nthHour = WritableUtils.readVInt(in);
		this.countViews = WritableUtils.readVInt(in);
	}

	@Override
	public int compareTo(PageDataValue o) {
		if (o == null) {
			return 0;
		} else if (nthHour != o.nthHour) {
			return (nthHour - o.nthHour);
		} else if (countViews != o.countViews) {
			return (countViews - o.countViews);
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
		return nthHour + "-" + countViews;
	}
}

