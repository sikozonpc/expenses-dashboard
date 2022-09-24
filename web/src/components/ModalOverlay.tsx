interface ModalOverlayProps {
  onClose: () => void;
}

export const ModalOverlay: React.FC<ModalOverlayProps> = ({ onClose }) => (
  <div
    onClick={onClose}
    tabIndex={-1}
    aria-hidden="true"
    className='bg-black opacity-50  overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0  w-full md:inset-0 h-modal md:h-full z-0'
  />
)