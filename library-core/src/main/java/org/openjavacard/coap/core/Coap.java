package org.openjavacard.coap.core;

public interface Coap {

    public static final byte CLA_SCOAP = (byte)0x80;
    public static final byte INS_SCOAP = (byte)0x80;

    public static final short VERSION_OFFSET  = 0;
    public static final byte  VERSION_MASK    = (byte)0xD0;
    public static final byte  VERSION_RFC7252 = (byte)0x40;

    public static final short TYPE_OFFSET = 0;
    public static final byte  TYPE_CON = (byte)0x00;
    public static final byte  TYPE_NC  = (byte)0x10;
    public static final byte  TYPE_ACK = (byte)0x20;
    public static final byte  TYPE_RST = (byte)0x30;

    public static final short TKL_OFFSET = 0;
    public static final short TKL_MASK = (byte)0x0F;

    public static final short CODE_OFFSET = 1;
    public static final byte CODE_CLASS_MASK = (byte)0xE0;
    public static final byte CODE_DETAIL_MASK = (byte)0x1F;

    public static final byte CLASS_REQUEST = (byte)0x00;
    public static final byte CLASS_RESPONSE = (byte)0x40;
    public static final byte CLASS_CLIENT_ERROR = (byte)0x80;
    public static final byte CLASS_SERVER_ERROR = (byte)0x90;

    public static final byte REQUEST_GET = (byte)0x01;
    public static final byte REQUEST_POST = (byte)0x02;
    public static final byte REQUEST_PUT = (byte)0x03;
    public static final byte REQUEST_DELETE = (byte)0x04;

    public static final byte RESPONSE_OK = (byte)0x40;
    public static final byte RESPONSE_CREATED = (byte)0x41;
    public static final byte RESPONSE_DELETED = (byte)0x42;
    public static final byte RESPONSE_VALID   = (byte)0x43;
    public static final byte RESPONSE_CHANGED = (byte)0x44;
    public static final byte RESPONSE_CONTENT = (byte)0x45;

    public static final byte CLIENT_BAD_REQUEST = (byte)0x80;
    public static final byte CLIENT_UNAUTHORIZED = (byte)0x81;
    public static final byte CLIENT_BAD_OPTION = (byte)0x82;
    public static final byte CLIENT_FORBIDDEN = (byte)83;
    public static final byte CLIENT_NOT_FOUND = (byte)84;
    public static final byte CLIENT_METHOD_NOT_ALLOWED = (byte)0x85;
    public static final byte CLIENT_NOT_ACCEPTABLE = (byte)0x86;
    public static final byte CLIENT_PRECONDITION_FAILED = (byte)0x8C;
    public static final byte CLIENT_REQUEST_ENTITY_TOO_LARGE = (byte)0x8D;
    public static final byte CLIENT_UNSUPPORTED_CONTENT_FORMAT = (byte)0x8F;

    public static final byte SERVER_INTERNAL_ERROR = (byte)0x90;
    public static final byte SERVER_NOT_IMPLEMENTED = (byte)0x91;
    public static final byte SERVER_BAD_GATEWAY = (byte)0x92;
    public static final byte SERVER_SERVICE_UNAVAILABLE = (byte)0x93;
    public static final byte SERVER_GATEWAY_TIMEOUT = (byte)0x94;
    public static final byte SERVER_PROXYING_NOT_SUPPORTED = (byte)0x95;

    public static final short MSGID_OFFSET = 2;

    public static final short OPTION_IF_MATCH = 1;
    public static final short OPTION_URI_HOST = 3;
    public static final short OPTION_ETAG = 4;
    public static final short OPTION_IF_NONE_MATCH = 5;
    public static final short OPTION_URI_PORT = 7;
    public static final short OPTION_LOCATION_PATH = 8;
    public static final short OPTION_URI_PATH = 11;
    public static final short OPTION_CONTENT_FORMAT = 12;
    public static final short OPTION_MAX_AGE = 14;
    public static final short OPTION_URI_QUERY = 15;
    public static final short OPTION_ACCEPT = 17;
    public static final short OPTION_LOCATION_QUERY = 20;
    public static final short OPTION_PROXY_URI = 35;
    public static final short OPTION_PROXY_SCHEME = 39;
    public static final short OPTION_SIZE1 = 60;

    public static final short MEDIA_TYPE_TEXT_PLAIN = 0;
    public static final short MEDIA_TYPE_TEXT_XML = 1;
    public static final short MEDIA_TYPE_TEXT_CSV = 2;
    public static final short MEDIA_TYPE_TEXT_HTML = 3;
    public static final short MEDIA_TYPE_IMAGE_GIF = 21; // 03
    public static final short MEDIA_TYPE_IMAGE_JPEG = 22; // 03
    public static final short MEDIA_TYPE_IMAGE_PNG = 23; // 03
    public static final short MEDIA_TYPE_IMAGE_TIFF = 24; // 03
    public static final short MEDIA_TYPE_AUDIO_RAW = 25; // 03
    public static final short MEDIA_TYPE_VIDEO_RAW = 26; // 03
    public static final short MEDIA_TYPE_APPLICATION_LINK_FORMAT = 40;
    public static final short MEDIA_TYPE_APPLICATION_XML = 41;
    public static final short MEDIA_TYPE_APPLICATION_OCTET_STREAM = 42;
    public static final short MEDIA_TYPE_APPLICATION_RDF_XML = 43;
    public static final short MEDIA_TYPE_APPLICATION_SOAP_XML = 44;
    public static final short MEDIA_TYPE_APPLICATION_ATOM_XML = 45;
    public static final short MEDIA_TYPE_APPLICATION_XMPP_XML = 46;
    public static final short MEDIA_TYPE_APPLICATION_EXI = 47;
    public static final short MEDIA_TYPE_APPLICATION_FASTINFOSET = 48; // 04
    public static final short MEDIA_TYPE_APPLICATION_SOAP_FASTINFOSET = 49; // 04
    public static final short MEDIA_TYPE_APPLICATION_JSON = 50; // 04
    public static final short MEDIA_TYPE_APPLICATION_X_OBIX_BINARY = 51; // 04
    public static final short MEDIA_TYPE_APPLICATION_CBOR = 60;
    public static final short MEDIA_TYPE_APPLICATION_SENML_JSON = 110;
    public static final short MEDIA_TYPE_APPLICATION_SENML_CBOR = 112;
    public static final short MEDIA_TYPE_APPLICATION_VND_OMA_LWM2M_TLV = 11542;
    public static final short MEDIA_TYPE_APPLICATION_VND_OMA_LWM2M_JSON = 11543;

}
