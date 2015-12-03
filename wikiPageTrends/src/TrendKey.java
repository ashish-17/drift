import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class TrendKey implements WritableComparable<TrendKey> {
	public String date;
	public Double trend;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    
    public TrendKey() {
		super();
		this.date = null;
		this.trend = null;
	}
    
	public TrendKey(String date, Double trend) {
		super();
		this.date = date;
		this.trend = trend;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, date);
		WritableUtils.writeString(out, trend.toString());		
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		date = WritableUtils.readString(in);
		trend = Double.parseDouble(WritableUtils.readString(in));
	}
	
	@Override
	public int compareTo(TrendKey arg0) {
		try {
			Date fmtDateThis = formatter.parse(date);
			Date fmtDateOther = formatter.parse(arg0.date);
			if (fmtDateThis.compareTo(fmtDateOther) == 0) {
				return (arg0.trend.compareTo(trend));
			} else {
				return fmtDateOther.compareTo(fmtDateThis);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return date + "," + trend.toString();
	}
}
