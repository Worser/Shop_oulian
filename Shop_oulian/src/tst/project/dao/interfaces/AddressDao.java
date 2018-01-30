package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.address.AddressBean;
import tst.project.bean.address.CityBean;
import tst.project.bean.address.RegionBean;
import tst.project.page.PageBean;

public interface AddressDao {
	/**
	 * 获得地域数据
	 * @param regionBean
	 * @return
	
	public List<RegionBean> getRegions(RegionBean regionBean); */
	
	/**
	 * 获得地域数据
	 * @param regionBean
	 * @return
	 */
	public List<CityBean> getCitys(CityBean cityBean);
	
	/**
	 * 添加地址
	 * @param addressBean
	 * @return
	 */
	public int insertAddress(AddressBean addressBean);
	
	/**
	 * 修改地址
	 * @param addressBean
	 * @return
	 */
	public int updateAddress(AddressBean addressBean);
	
	/**
	 * 获得自己的地址列表
	 * @param addressBean
	 * @return
	 */
	public  List<AddressBean> getOwnerAddress(AddressBean addressBean);
	public  List<AddressBean> getOwnerAddress(AddressBean addressBean,PageBean pageBean);

	/**
	 * 刪除地址
	 * @param addressBean
	 * @return
	 */
	public int deleteAddress(AddressBean addressBean);
	
	/**
	 * 设置默认地址
	 * @return
	 */
	public int setDefaultAddress(AddressBean addressBean);
	
	
	/**
	 * 获得默认地址
	 * @param addressBean
	 * @return
	 */
	public AddressBean getDefaultAddress(AddressBean addressBean);
	
	
	/**
	 * 通过地址Id得到详细信息
	 * @param addressBean
	 * @return
	 */
	public AddressBean getAddressById(AddressBean addressBean);
}