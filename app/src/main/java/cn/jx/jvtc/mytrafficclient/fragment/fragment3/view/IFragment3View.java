package cn.jx.jvtc.mytrafficclient.fragment.fragment3.view;

public interface IFragment3View {
    //响应标记
    int RESPONSE_CAR = 1;//小车信息
    int RESPONSE_CARPECCANCY = 2;//违章信息
    int RESPONSE_PERSON =3;//车主信息;
    int RESPONSE_PECCANCY=4;//违章代码信息;
    <T> void response(T response, int responseFlag);

}
