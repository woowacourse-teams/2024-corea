import useDropdown from "@/hooks/common/useDropdown";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/roomDetailPage/feedbackProcessInfo/FeedbackProcessInfo.style";

interface ExpandableSectionProps {
  title: string;
  children: React.ReactNode;
}

const FeedbackProcessInfo = ({ title, children }: ExpandableSectionProps) => {
  const { isDropdownOpen, handleToggleDropdown } = useDropdown();

  const handleProcessInfoClick = (event: React.MouseEvent) => {
    event.preventDefault();
    handleToggleDropdown();
  };

  return (
    <S.ExpandableSection>
      <S.StyledTitle onClick={handleProcessInfoClick} tabIndex={0} aria-expanded={isDropdownOpen}>
        {title}
        <Icon kind={isDropdownOpen ? "arrowDropUp" : "arrowDropDown"} size="2rem" />
      </S.StyledTitle>
      {isDropdownOpen && <S.ExpandableContent>{children}</S.ExpandableContent>}
    </S.ExpandableSection>
  );
};

export default FeedbackProcessInfo;
