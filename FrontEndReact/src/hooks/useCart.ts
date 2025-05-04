import { useCartContext } from '../contexts/CartContext.tsx'

export const useCart = () => {
  return useCartContext()
}
