import styled from "styled-components";

export const CalendarContainer = styled.section`
  width: 400px;
  padding: 2rem;
  background-color: ${({ theme }) => theme.COLOR.primary1};
  border-radius: 10px;
`;

export const CalendarHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem;
`;

export const CalendarSelectedDay = styled.span`
  font: ${({ theme }) => theme.TEXT.medium_bold};
`;

export const CalendarAdjustWrapper = styled.div`
  display: flex;
`;

export const CalendarAdjustPrevious = styled.button`
  display: flex;
  align-items: center;

  text-align: center;

  background: transparent;
  outline: none;
`;

export const CalendarAdjustNext = styled.button`
  display: flex;
  align-items: center;

  text-align: center;

  background: transparent;
  outline: none;
`;

export const Table = styled.table`
  width: 100%;
`;

export const Thead = styled.thead``;

export const Tbody = styled.tbody``;

export const Tr = styled.tr``;

export const Th = styled.th`
  padding: 1rem;
  font: ${({ theme }) => theme.TEXT.medium};
`;

export const Td = styled.td<{ $isSelected: boolean; $isAvailableClick: boolean }>`
  cursor: ${({ $isAvailableClick }) => $isAvailableClick && "pointer"};

  padding: 1rem;

  font: ${({ theme }) => theme.TEXT.medium_bold};
  color: ${({ $isAvailableClick, theme }) => !$isAvailableClick && theme.COLOR.grey1};
  text-align: center;

  background-color: ${({ $isSelected, theme }) => $isSelected && theme.COLOR.white};
  border-radius: ${({ $isSelected }) => $isSelected && "10px"};

  &:hover {
    background-color: ${({ theme, $isAvailableClick }) => $isAvailableClick && theme.COLOR.white};
    border-radius: ${({ $isAvailableClick }) => $isAvailableClick && "10px"};
  }
`;
