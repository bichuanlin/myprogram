package cn.jx.jvtc.mytrafficclient.fragment.fragment3.presenter;

public interface CallBack<T> {
    void onSuccess(T response);
    void onError(String t);
}
