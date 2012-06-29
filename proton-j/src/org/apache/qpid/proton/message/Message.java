package org.apache.qpid.proton.message;

import java.nio.ByteBuffer;
import java.util.Date;
import org.apache.qpid.proton.codec.DecoderImpl;
import org.apache.qpid.proton.codec.EncoderImpl;
import org.apache.qpid.proton.type.*;
import org.apache.qpid.proton.type.messaging.*;

public class Message
{
    public static final short DEFAULT_PRIORITY = 4;

    private final AMQPMessageFormat _parser = new AMQPMessageFormat();

    private Header _header;
    private DeliveryAnnotations _deliveryAnnotations;
    private MessageAnnotations _messageAnnotations;
    private Properties _properties;
    private ApplicationProperties _applicationProperties;
    private Section _body;
    private Footer _footer;
    private MessageFormat _format = MessageFormat.DATA;

    public Message()
    {
    }

    Message(Header header, DeliveryAnnotations deliveryAnnotations, MessageAnnotations messageAnnotations,
            Properties properties, ApplicationProperties applicationProperties, Section body, Footer footer)
    {
        _header = header;
        _deliveryAnnotations = deliveryAnnotations;
        _messageAnnotations = messageAnnotations;
        _properties = properties;
        _applicationProperties = applicationProperties;
        _body = body;
        _footer = footer;
    }

    public boolean isDurable()
    {
        return (_header == null || _header.getDurable() == null) ? false : _header.getDurable();
    }


    public long getDeliveryCount()
    {
        return (_header == null || _header.getDeliveryCount() == null) ? 0l : _header.getDeliveryCount().longValue();
    }


    public short getPriority()
    {
        return (_header == null || _header.getPriority() == null)
                       ? DEFAULT_PRIORITY
                       : _header.getPriority().shortValue();
    }

    public boolean isFirstAcquirer()
    {
        return (_header == null || _header.getFirstAcquirer() == null) ? false : _header.getFirstAcquirer();
    }

    public long getTtl()
    {
        return (_header == null || _header.getTtl() == null) ? 0l : _header.getTtl().longValue();
    }

    public void setDurable(boolean durable)
    {
        if (_header == null)
        {
            if (durable)
            {
                _header = new Header();
            }
            else
            {
                return;
            }
        }
        _header.setDurable(durable);
    }

    public void setTtl(long ttl)
    {

        if (_header == null)
        {
            if (ttl != 0l)
            {
                _header = new Header();
            }
            else
            {
                return;
            }
        }
        _header.setTtl(UnsignedInteger.valueOf(ttl));
    }

    public void setDeliveryCount(long deliveryCount)
    {
        if (_header == null)
        {
            if (deliveryCount == 0l)
            {
                return;
            }
            _header = new Header();
        }
        _header.setDeliveryCount(UnsignedInteger.valueOf(deliveryCount));
    }


    public void setFirstAcquirer(boolean firstAcquirer)
    {

        if (_header == null)
        {
            if (!firstAcquirer)
            {
                return;
            }
            _header = new Header();
        }
        _header.setFirstAcquirer(firstAcquirer);
    }

    public void setPriority(short priority)
    {

        if (_header == null)
        {
            if (priority == DEFAULT_PRIORITY)
            {
                return;
            }
            _header = new Header();
        }
        _header.setPriority(UnsignedByte.valueOf((byte) priority));
    }

    public Object getMessageId()
    {
        return _properties == null ? null : _properties.getMessageId();
    }

    public long getGroupSequence()
    {
        return (_properties == null || _properties.getGroupSequence() == null) ? 0l : _properties.getGroupSequence().intValue();
    }

    public String getReplyToGroupId()
    {
        return _properties == null ? null : _properties.getReplyToGroupId();
    }

    public long getCreationTime()
    {
        return (_properties == null || _properties.getCreationTime() == null) ? 0l : _properties.getCreationTime().getTime();
    }

    public String getAddress()
    {
        return _properties == null ? null : _properties.getTo();
    }

    public byte[] getUserId()
    {
        if(_properties == null || _properties.getUserId() == null)
        {
            return null;
        }
        else
        {
            final Binary userId = _properties.getUserId();
            byte[] id = new byte[userId.getLength()];
            System.arraycopy(userId.getArray(),userId.getArrayOffset(),id,0,userId.getLength());
            return id;
        }

    }

    public String getReplyTo()
    {
        return _properties == null ? null : _properties.getReplyTo();
    }

    public String getGroupId()
    {
        return _properties == null ? null : _properties.getGroupId();
    }

    public String getContentType()
    {
        return (_properties == null || _properties.getContentType() == null) ? null : _properties.getContentType().toString();
    }

    public long getExpiryTime()
    {
        return (_properties == null || _properties.getAbsoluteExpiryTime() == null) ? 0l : _properties.getAbsoluteExpiryTime().getTime();
    }

    public Object getCorrelationId()
    {
        return (_properties == null) ? null : _properties.getCorrelationId();
    }

    public String getContentEncoding()
    {
        return (_properties == null || _properties.getContentEncoding() == null) ? null : _properties.getContentEncoding().toString();
    }

    public String getSubject()
    {
        return _properties == null ? null : _properties.getSubject();
    }

    public void setGroupSequence(long groupSequence)
    {
        if(_properties == null)
        {
            if(groupSequence == 0l)
            {
                return;
            }
            else
            {
                _properties = new Properties();
            }
        }
        _properties.setGroupSequence(UnsignedInteger.valueOf((int) groupSequence));
    }

    public void setUserId(byte[] userId)
    {
        if(userId == null)
        {
            if(_properties != null)
            {
                _properties.setUserId(null);
            }

        }
        else
        {
            if(_properties == null)
            {
                _properties = new Properties();
            }
            byte[] id = new byte[userId.length];
            System.arraycopy(userId, 0, id,0, userId.length);
            _properties.setUserId(new Binary(id));
        }
    }

    public void setCreationTime(long creationTime)
    {
        if(_properties == null)
        {
            if(creationTime == 0l)
            {
                return;
            }
            _properties = new Properties();

        }
        _properties.setCreationTime(new Date(creationTime));
    }

    public void setSubject(String subject)
    {
        if(_properties == null)
        {
            if(subject == null)
            {
                return;
            }
            _properties = new Properties();
        }
        _properties.setSubject(subject);
    }

    public void setGroupId(String groupId)
    {
        if(_properties == null)
        {
            if(groupId == null)
            {
                return;
            }
            _properties = new Properties();
        }
        _properties.setGroupId(groupId);
    }

    public void setAddress(String to)
    {
        if(_properties == null)
        {
            if(to == null)
            {
                return;
            }
            _properties = new Properties();
        }
        _properties.setTo(to);
    }

    public void setExpiryTime(long absoluteExpiryTime)
    {
        if(_properties == null)
        {
            if(absoluteExpiryTime == 0l)
            {
                return;
            }
            _properties = new Properties();

        }
        _properties.setAbsoluteExpiryTime(new Date(absoluteExpiryTime));
    }

    public void setReplyToGroupId(String replyToGroupId)
    {
        if(_properties == null)
        {
            if(replyToGroupId == null)
            {
                return;
            }
            _properties = new Properties();
        }
        _properties.setReplyToGroupId(replyToGroupId);
    }

    public void setContentEncoding(String contentEncoding)
    {
        if(_properties == null)
        {
            if(contentEncoding == null)
            {
                return;
            }
            _properties = new Properties();
        }
        _properties.setContentEncoding(Symbol.valueOf(contentEncoding));
    }

    public void setContentType(String contentType)
    {
        if(_properties == null)
        {
            if(contentType == null)
            {
                return;
            }
            _properties = new Properties();
        }
        _properties.setContentType(Symbol.valueOf(contentType));
    }

    public void setReplyTo(String replyTo)
    {

        if(_properties == null)
        {
            if(replyTo == null)
            {
                return;
            }
            _properties = new Properties();
        }
        _properties.setReplyTo(replyTo);
    }

    public void setCorrelationId(Object correlationId)
    {

        if(_properties == null)
        {
            if(correlationId == null)
            {
                return;
            }
            _properties = new Properties();
        }
        _properties.setCorrelationId(correlationId);
    }

    public void setMessageId(Object messageId)
    {

        if(_properties == null)
        {
            if(messageId == null)
            {
                return;
            }
            _properties = new Properties();
        }
        _properties.setMessageId(messageId);
    }


    Header getHeader()
    {
        return _header;
    }

    DeliveryAnnotations getDeliveryAnnotations()
    {
        return _deliveryAnnotations;
    }

    MessageAnnotations getMessageAnnotations()
    {
        return _messageAnnotations;
    }

    Properties getProperties()
    {
        return _properties;
    }

    ApplicationProperties getApplicationProperties()
    {
        return _applicationProperties;
    }

    Section getBody()
    {
        return _body;
    }

    Footer getFooter()
    {
        return _footer;
    }

    public int decode(byte[] data, int offset, int length)
    {
        DecoderImpl decoder = new DecoderImpl();
        EncoderImpl encoder = new EncoderImpl(decoder);

        AMQPDefinedTypes.registerAllTypes(decoder);
        final ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
        decoder.setByteBuffer(buffer);

        _header = null;
        _deliveryAnnotations = null;
        _messageAnnotations = null;
        _properties = null;
        _applicationProperties = null;
        _body = null;
        _footer = null;
        Section section = null;

        if(buffer.hasRemaining())
        {
            section = (Section) decoder.readObject();
        }
        if(section instanceof Header)
        {
            _header = (Header) section;
            if(buffer.hasRemaining())
            {
                section = (Section) decoder.readObject();
            }
            else
            {
                section = null;
            }

        }
        if(section instanceof DeliveryAnnotations)
        {
            _deliveryAnnotations = (DeliveryAnnotations) section;

            if(buffer.hasRemaining())
            {
                section = (Section) decoder.readObject();
            }
            else
            {
                section = null;
            }

        }
        if(section instanceof MessageAnnotations)
        {
            _messageAnnotations = (MessageAnnotations) section;

            if(buffer.hasRemaining())
            {
                section = (Section) decoder.readObject();
            }
            else
            {
                section = null;
            }

        }
        if(section instanceof Properties)
        {
            _properties = (Properties) section;

            if(buffer.hasRemaining())
            {
                section = (Section) decoder.readObject();
            }
            else
            {
                section = null;
            }

        }
        if(section instanceof ApplicationProperties)
        {
            _applicationProperties = (ApplicationProperties) section;

            if(buffer.hasRemaining())
            {
                section = (Section) decoder.readObject();
            }
            else
            {
                section = null;
            }

        }
        if(section != null && !(section instanceof Footer))
        {
            _body = section;

            if(buffer.hasRemaining())
            {
                section = (Section) decoder.readObject();
            }
            else
            {
                section = null;
            }

        }
        if(section instanceof Footer)
        {
            _footer = (Footer) section;

        }

        return length-buffer.remaining();

    }

    public int encode(byte[] data, int offset, int length)
    {
        DecoderImpl decoder = new DecoderImpl();
        EncoderImpl encoder = new EncoderImpl(decoder);
        AMQPDefinedTypes.registerAllTypes(decoder);
        final ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
        encoder.setByteBuffer(buffer);

        if(getHeader() != null)
        {
            encoder.writeObject(getHeader());
        }
        if(getDeliveryAnnotations() != null)
        {
            encoder.writeObject(getDeliveryAnnotations());
        }
        if(getMessageAnnotations() != null)
        {
            encoder.writeObject(getDeliveryAnnotations());
        }
        if(getProperties() != null)
        {
            encoder.writeObject(getProperties());
        }
        if(getApplicationProperties() != null)
        {
            encoder.writeObject(getApplicationProperties());
        }
        if(getBody() != null)
        {
            encoder.writeObject(getBody());
        }
        if(getFooter() != null)
        {
            encoder.writeObject(getFooter());
        }

        return length - buffer.remaining();
    }

    public void load(Object data)
    {
        switch (_format)
        {
            case DATA:
                Binary binData;
                if(data instanceof byte[])
                {
                    binData = new Binary((byte[])data);
                }
                else if(data instanceof Binary)
                {
                    binData = (Binary) data;
                }
                else if(data instanceof String)
                {
                    final String strData = (String) data;
                    byte[] bin = new byte[strData.length()];
                    for(int i = 0; i < bin.length; i++)
                    {
                        bin[i] = (byte) strData.charAt(i);
                    }
                    binData = new Binary(bin);
                }
                else
                {
                    binData = null;
                }
                _body = new Data(binData);
                break;
            case TEXT:
                _body = new AmqpValue(data == null ? "" : data.toString());
                break;
            default:
                // AMQP
                _body = new AmqpValue(parseAMQPFormat((String) data));
        }

    }

    public Object save()
    {
        switch (_format)
        {
            case DATA:
                if(_body instanceof Data)
                {
                    return ((Data)_body).getValue().getArray();
                }
                else return null;
            case AMQP:
                if(_body instanceof AmqpValue)
                {
                    return toAMQPFormat(((AmqpValue) _body).getValue());
                }
                else
                {
                    return null;
                }
            case TEXT:
                if(_body instanceof AmqpValue)
                {
                    final Object value = ((AmqpValue) _body).getValue();
                    return value == null ? "" : value.toString();
                }
                return null;
            default:
                return null;
        }
    }

    private String toAMQPFormat(Object value)
    {
        return _parser.encode(value);
    }

    private Object parseAMQPFormat(String value)
    {

        Object obj = _parser.format(value);
        return obj;
    }

    public void setMessageFormat(MessageFormat format)
    {
        _format = format;
    }

    public MessageFormat getMessageFormat()
    {
        return _format;
    }

    public void clear()
    {
        _body = null;
    }

    public MessageError getError()
    {
        return MessageError.OK;
    }

}
