//$Id: MonetoryAmountUserType.java 6235 2005-03-29 03:17:49Z oneovthafew $
package mapping.cut;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.CurrencyType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

/**
 * @author Gavin King
 */
public class MonetoryAmountUserType implements CompositeUserType {

	public String[] getPropertyNames() {
		return new String[] { "amount", "currency" };  //$NON-NLS-1$//$NON-NLS-2$
	}

	public Type[] getPropertyTypes() {
		return new Type[] { BigDecimalType.INSTANCE, CurrencyType.INSTANCE };
	}

	public Object getPropertyValue(Object component, int property) throws HibernateException {
		MonetoryAmount ma = (MonetoryAmount) component;
		return property==0 ? (Object) ma.getAmount() : (Object) ma.getCurrency();
	}

	public void setPropertyValue(Object component, int property, Object value)
			throws HibernateException {
		MonetoryAmount ma = (MonetoryAmount) component;
		if ( property==0 ) {
			ma.setAmount( (BigDecimal) value );
		}
		else {
			ma.setCurrency( (Currency) value );
		}
	}

	public Class returnedClass() {
		return MonetoryAmount.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if (x==y) return true;
		if (x==null || y==null) return false;
		MonetoryAmount mx = (MonetoryAmount) x;
		MonetoryAmount my = (MonetoryAmount) y;
		return mx.getAmount().equals( my.getAmount() ) &&
			mx.getCurrency().equals( my.getCurrency() );
	}

	public int hashCode(Object x) throws HibernateException {
		return ( (MonetoryAmount) x ).getAmount().hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		BigDecimal amt = (BigDecimal) BigDecimalType.INSTANCE.nullSafeGet( rs, names[0],session );
		Currency cur = (Currency) CurrencyType.INSTANCE.nullSafeGet( rs, names[1], session );
		if (amt==null) return null;
		return new MonetoryAmount(amt, cur);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		MonetoryAmount ma = (MonetoryAmount) value;
		BigDecimal amt = ma == null ? null : ma.getAmount();
		Currency cur = ma == null ? null : ma.getCurrency();
		BigDecimalType.INSTANCE.nullSafeSet(st, amt, index, session);
		CurrencyType.INSTANCE.nullSafeSet(st, cur, index+1, session);
	}

	public Object deepCopy(Object value) throws HibernateException {
		MonetoryAmount ma = (MonetoryAmount) value;
		return new MonetoryAmount( ma.getAmount(), ma.getCurrency() );
	}

	public boolean isMutable() {
		return true;
	}

	public Serializable disassemble(Object value, SessionImplementor session)
			throws HibernateException {
		return (Serializable) deepCopy(value);
	}

	public Object assemble(Serializable cached, SessionImplementor session, Object owner)
			throws HibernateException {
		return deepCopy(cached);
	}

	public Object replace(Object original, Object target, SessionImplementor session, Object owner)
			throws HibernateException {
		return deepCopy(original); //TODO: improve
	}

}