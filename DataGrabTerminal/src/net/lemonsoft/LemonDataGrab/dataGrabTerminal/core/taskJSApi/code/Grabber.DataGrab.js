function DataGrab(grabberIdentity) {

    /**
     * 作用于的采集器
     */
    var grabberIdentity = grabberIdentity;

    /**
     * 在数据采集容器中执行指定的JS代码,并且返回代码执行的返回值
     * @param jsCode 要在采集容器中执行的JS代码
     */
    this.executeJS = function (jsCode) {
        return window.grabber.executeJS(grabberIdentity , jsCode);
    };

    /**
     * 获取指定Dom元素的attribute参数值
     * @param domSelector 指定元素的选择器
     * @param attributeName 要获取的属性名
     * @returns {string} 获取到的属性值
     */
    this.getDomAttribute = function (domSelector , attributeName) {
        return this.executeJS("document.querySelector('" + domSelector + "').getAttribute('" + attributeName + "')");
    };

    /**
     * 获取指定Dom元素的属性值
     * @param domSelector
     * @param propertyName
     */
    this.getDomProperty = function (domSelector, propertyName) {
        return this.executeJS("document.querySelector('" + domSelector + "')." + propertyName);
    };

    /**
     * 获取指定元素的innerHTML
     * @param domSelector 要获取innerHTML的元素的选择器
     * @returns {string} 获取到的innerHTML
     */
    this.getInnerHTML = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector , "innerHTML");
            Log.success("根据脚本命令获取指定元素的innerHTML成功");
            return result;
        }catch (e){
            Log.warn("获取指定元素的innerHTML失败:" + e);
            return null;
        }
    };

    /**
     * 获取指定元素的outerHTML
     * @param domSelector 要获取outerHTML的元素的选择器
     * @returns {string} 获取到的outerHTML
     */
    this.getOuterHTML = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector , "outerHTML");
            Log.success("根据脚本命令获取指定元素的outerHTML成功");
            return result;
        }catch (e){
            Log.warn("获取指定元素的outerHTML失败:" + e);
            return null;
        }
    };

    /**
     * 获取指定元素的innerText
     * @param domSelector 要获取innerText的元素的选择器
     * @returns {string} 获取到的outerHTML
     */
    this.getInnerText = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector , "innerText");
            Log.success("根据脚本命令获取指定元素的innerText成功");
            return result;
        }catch (e){
            Log.warn("获取指定元素的innerText失败:" + e);
            return null;
        }
    };

    /**
     * 获取当前采集数据的界面的URL
     * AID: 2d
     *
     * @returns String URL字符串
     */
    this.getURL = function () {
        try {
            var result = this.executeJS("location.href");
            Log.success("根据脚本命令获取当前采集数据的界面的URL成功");
            return result;
        }catch (e){
            Log.warn("获取当前采集数据的界面的URL失败:" + e);
            return null;
        }
    };

    /**
     * 获取指定图片Dom元素的图片URL
     * AID: 2e
     *
     * @param domSelector 要获取图片URL的图片Dom元素的css选择器
     * @returns String 图片URL字符串
     */
    this.getImgDomURL = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector , "src");
            Log.success("根据脚本命令获取指定图片Dom元素的图片URL成功");
            return result;
        }catch (e){
            Log.warn("获取指定图片Dom元素的图片URL失败:" + e);
            return null;
        }
    };

    /**
     * 获取指定A标记的的链接URL
     * AID: 2f
     *
     * @param domSelector 要获取超链接URL的A标记的css选择器字符串
     * @returns String A标记的链接的URL字符串
     */
    this.getADomURL = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector, "href");
            Log.success("根据脚本命令获取指定A标记的的链接URL成功");
            return result;
        }catch (e){
            Log.warn("获取指定A标记的的链接URL失败:" + e);
            return null;
        }
    };

}