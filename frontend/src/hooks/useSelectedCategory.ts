import { useState } from "react";

const useSelectedCategory = (initialCategory: string = "all") => {
  const [selectedCategory, setSelectedCategory] = useState(() => {
    return localStorage.getItem("selectedCategory") || initialCategory;
  });

  const handleSelectedCategory = (category: string) => {
    setSelectedCategory(category);
    localStorage.setItem("selectedCategory", category);
  };

  return { selectedCategory, handleSelectedCategory };
};

export default useSelectedCategory;
