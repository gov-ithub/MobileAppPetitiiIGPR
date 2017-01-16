using Caliburn.Micro;

namespace Tickets.ViewModels
{
    public class TicketsWithoutAccountPageViewModel : PropertyChangedBase
    {
        private readonly INavigationService _navigationService;

        public TicketsWithoutAccountPageViewModel(INavigationService navigationService)
        {
            _navigationService = navigationService;
        }

        public void SendTicket()
        {
            _navigationService.For<SetupTicketWithoutAccountPageViewModel>().Navigate();
        }
    }
}
