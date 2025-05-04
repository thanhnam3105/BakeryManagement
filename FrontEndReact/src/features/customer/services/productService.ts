import img1 from '../../../assets/images/1.jpg';
import img2 from '../../../assets/images/2.jpg';
import img3 from '../../../assets/images/3.jpg';

const fakeProducts = [
  { id: 1, name: 'Bánh Kem', price: 100000, description: 'Bánh kem sinh nhật thơm ngon', imageUrl: img1 },
  { id: 2, name: 'Bánh Mì', price: 20000, description: 'Bánh mì nóng giòn', imageUrl: img2 },
  { id: 3, name: 'Bánh Cookie', price: 50000, description: 'Cookie giòn tan', imageUrl: img3 },
];

export async function getProducts() {
  return fakeProducts;
}

export async function getProductById(id) {
  return fakeProducts.find(p => p.id === parseInt(id));
}
