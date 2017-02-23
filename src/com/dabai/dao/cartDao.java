package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;

import com.dabai.vo.cartItem;
import com.dabai.vo.carts;

public interface cartDao {
	/**加入到购物车总表
	 * 
	 *@param	cart类
	 *@return	操作处理结果
	 * @throws SQLException 
	 *@throw	
	 * */
	public int addCart(carts cart) throws SQLException;
	
	/**加入到购物车单表
	 * 
	 *@param	cartItem类
	 *@return	操作处理结果
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean addCartItem(cartItem cartitem) throws SQLException;
	
	/**根据用户id查找是否该用户有购物车记录
	 * 
	 *@param	用户id
	 *@return	操作处理结果
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean findUser(int id) throws SQLException;
	
	/**根据用户id返回相应购物车信息
	 * 
	 *@param	用户id
	 *@return	操作处理结果
	 * @throws SQLException 
	 *@throw	
	 * */
	public carts findCart(int userId) throws SQLException;

	/**根据用户id查找相应购物车总表id
	 * 若id不存在则创建，否则取出
	 * 
	 *@param	用户id
	 *@return	购物车id
	 * @throws SQLException 
	 *@throw	
	 * */
	public int getCartId(int userId) throws SQLException;

	/**根据购物车的编号查找购物车所有物品
	 * 
	 *@param	购物车编号
	 *@return	所有物品
	 * @throws SQLException 
	 *@throw	
	 * */
	public List<cartItem> getAll(int cartId) throws SQLException;

	/**书本已存在数据库中，则直接修改数量
	 * 
	 *@param	qunatity	书本数量
	 *@param	bookId	书本编号
	 *@return	判断添加是否成功
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean modQuantity(int quantity, int bookId) throws SQLException;

	/**根据书本编号删除购物车信息
	 * 
	 *@param	bookId	书本编号
	 *@return	判断删除是否成功
	 * @throws SQLException 
	 * */
	public boolean delCartItem(int bookId) throws SQLException;

	/**根据书本编号获取该购物车信息
	 * 
	 *@param	bookId	书本编号
	 *@return	购物车书本信息
	 * @throws SQLException 
	 * */
	public cartItem getCartItem(int bookId) throws SQLException;
}
