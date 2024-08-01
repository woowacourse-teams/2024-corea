import { useEffect, useRef, useState } from "react";

const isDescendant = (parent: Node, child: Node | null): boolean => {
  let node = child;
  while (node !== null) {
    if (node === parent) {
      return true;
    }
    node = node.parentNode;
  }
  return false;
};

const useDropdown = () => {
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef(null);

  const handleOutsideClick = (event: MouseEvent) => {
    const targetNode = event.target as Node;
    if (dropdownRef.current && !isDescendant(dropdownRef.current, targetNode)) {
      setIsOpen(false);
    }
  };

  const handleToggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  useEffect(() => {
    document.addEventListener("click", handleOutsideClick);
    return () => {
      document.removeEventListener("click", handleOutsideClick);
    };
  }, []);

  return { isOpen, handleToggleDropdown, dropdownRef };
};

export default useDropdown;
